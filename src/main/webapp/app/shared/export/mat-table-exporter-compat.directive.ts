import { Directive, ElementRef, Input } from '@angular/core';

interface ExportTableOptions {
  fileName?: string;
  sheet?: string;
}

@Directive({
  selector: '[matTableExporter]',
  exportAs: 'matTableExporter',
  standalone: true,
})
export class MatTableExporterCompatDirective {
  @Input() hiddenColumns: number[] = [];

  constructor(private elementRef: ElementRef<HTMLElement>) {}

  exportTable(type: string = 'xlsx', options: ExportTableOptions = {}): void {
    if (type !== 'xlsx') {
      throw new Error(`Unsupported export type: ${type}`);
    }

    const rows = this.extractRenderedRows();
    if (rows.length === 0) {
      return;
    }

    const workbookBytes = this.createXlsx(rows, options.sheet || 'Sheet1');
    this.download(workbookBytes, this.normalizeFileName(options.fileName || 'export'));
  }

  private extractRenderedRows(): string[][] {
    const root = this.elementRef.nativeElement;
    const hiddenColumnIndexes = new Set((this.hiddenColumns || []).map(Number));

    const rowElements = Array.from(
      root.querySelectorAll<HTMLElement>(
        [
          'mat-header-row',
          'mat-row',
          'tr.mat-header-row',
          'tr.mat-row',
          'tr.mat-mdc-header-row',
          'tr.mat-mdc-row',
          '.mat-header-row',
          '.mat-row',
          '.mat-mdc-header-row',
          '.mat-mdc-row',
        ].join(',')
      )
    );

    return rowElements
      .map(row => {
        const cells = Array.from(
          row.querySelectorAll<HTMLElement>(
            [
              'mat-header-cell',
              'mat-cell',
              'th',
              'td',
              '.mat-header-cell',
              '.mat-cell',
              '.mat-mdc-header-cell',
              '.mat-mdc-cell',
            ].join(',')
          )
        );

        return cells
          .filter((_, index) => !hiddenColumnIndexes.has(index))
          .map(cell => this.normalizeCellText(cell.textContent || ''));
      })
      .filter(row => row.some(cell => cell.length > 0));
  }

  private createXlsx(rows: string[][], sheetName: string): Uint8Array {
    const safeSheetName = this.normalizeSheetName(sheetName);

    const files: Record<string, string> = {
      '[Content_Types].xml': this.contentTypesXml(),
      '_rels/.rels': this.rootRelsXml(),
      'docProps/app.xml': this.appXml(safeSheetName),
      'docProps/core.xml': this.coreXml(),
      'xl/workbook.xml': this.workbookXml(safeSheetName),
      'xl/_rels/workbook.xml.rels': this.workbookRelsXml(),
      'xl/styles.xml': this.stylesXml(),
      'xl/worksheets/sheet1.xml': this.sheetXml(rows),
    };

    return this.zipStore(files);
  }

  private sheetXml(rows: string[][]): string {
    const rowXml = rows
      .map((row, rowIndex) => {
        const rowNumber = rowIndex + 1;
        const cellsXml = row
          .map((value, columnIndex) => {
            const cellReference = `${this.columnName(columnIndex + 1)}${rowNumber}`;
            return `<c r="${cellReference}" t="inlineStr"><is><t>${this.xmlEscape(value)}</t></is></c>`;
          })
          .join('');

        return `<row r="${rowNumber}">${cellsXml}</row>`;
      })
      .join('');

    return `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<worksheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
  <sheetData>${rowXml}</sheetData>
</worksheet>`;
  }

  private contentTypesXml(): string {
    return `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
  <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
  <Default Extension="xml" ContentType="application/xml"/>
  <Override PartName="/docProps/app.xml" ContentType="application/vnd.openxmlformats-officedocument.extended-properties+xml"/>
  <Override PartName="/docProps/core.xml" ContentType="application/vnd.openxmlformats-package.core-properties+xml"/>
  <Override PartName="/xl/workbook.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml"/>
  <Override PartName="/xl/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml"/>
  <Override PartName="/xl/worksheets/sheet1.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml"/>
</Types>`;
  }

  private rootRelsXml(): string {
    return `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
  <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="xl/workbook.xml"/>
  <Relationship Id="rId2" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties" Target="docProps/core.xml"/>
  <Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties" Target="docProps/app.xml"/>
</Relationships>`;
  }

  private workbookXml(sheetName: string): string {
    return `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<workbook xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
  <sheets>
    <sheet name="${this.xmlEscape(sheetName)}" sheetId="1" r:id="rId1"/>
  </sheets>
</workbook>`;
  }

  private workbookRelsXml(): string {
    return `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
  <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet" Target="worksheets/sheet1.xml"/>
  <Relationship Id="rId2" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Target="styles.xml"/>
</Relationships>`;
  }

  private stylesXml(): string {
    return `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<styleSheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
  <fonts count="1"><font><sz val="11"/><name val="Calibri"/></font></fonts>
  <fills count="1"><fill><patternFill patternType="none"/></fill></fills>
  <borders count="1"><border><left/><right/><top/><bottom/><diagonal/></border></borders>
  <cellStyleXfs count="1"><xf numFmtId="0" fontId="0" fillId="0" borderId="0"/></cellStyleXfs>
  <cellXfs count="1"><xf numFmtId="0" fontId="0" fillId="0" borderId="0" xfId="0"/></cellXfs>
</styleSheet>`;
  }

  private appXml(sheetName: string): string {
    return `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties" xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
  <Application>npg-portal</Application>
  <TitlesOfParts>
    <vt:vector size="1" baseType="lpstr"><vt:lpstr>${this.xmlEscape(sheetName)}</vt:lpstr></vt:vector>
  </TitlesOfParts>
</Properties>`;
  }

  private coreXml(): string {
    const now = new Date().toISOString();

    return `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <dc:creator>npg-portal</dc:creator>
  <dcterms:created xsi:type="dcterms:W3CDTF">${now}</dcterms:created>
  <dcterms:modified xsi:type="dcterms:W3CDTF">${now}</dcterms:modified>
</cp:coreProperties>`;
  }

  private zipStore(files: Record<string, string>): Uint8Array {
    const encoder = new TextEncoder();
    const localParts: Uint8Array[] = [];
    const centralParts: Uint8Array[] = [];
    let offset = 0;

    for (const [fileName, content] of Object.entries(files)) {
      const nameBytes = encoder.encode(fileName);
      const dataBytes = encoder.encode(content);
      const crc = this.crc32(dataBytes);

      const localHeader = new Uint8Array(30 + nameBytes.length);
      const localView = new DataView(localHeader.buffer);

      localView.setUint32(0, 0x04034b50, true);
      localView.setUint16(4, 20, true);
      localView.setUint16(6, 0, true);
      localView.setUint16(8, 0, true);
      localView.setUint16(10, 0, true);
      localView.setUint16(12, 0, true);
      localView.setUint32(14, crc, true);
      localView.setUint32(18, dataBytes.length, true);
      localView.setUint32(22, dataBytes.length, true);
      localView.setUint16(26, nameBytes.length, true);
      localView.setUint16(28, 0, true);
      localHeader.set(nameBytes, 30);

      localParts.push(localHeader, dataBytes);

      const centralHeader = new Uint8Array(46 + nameBytes.length);
      const centralView = new DataView(centralHeader.buffer);

      centralView.setUint32(0, 0x02014b50, true);
      centralView.setUint16(4, 20, true);
      centralView.setUint16(6, 20, true);
      centralView.setUint16(8, 0, true);
      centralView.setUint16(10, 0, true);
      centralView.setUint16(12, 0, true);
      centralView.setUint16(14, 0, true);
      centralView.setUint32(16, crc, true);
      centralView.setUint32(20, dataBytes.length, true);
      centralView.setUint32(24, dataBytes.length, true);
      centralView.setUint16(28, nameBytes.length, true);
      centralView.setUint16(30, 0, true);
      centralView.setUint16(32, 0, true);
      centralView.setUint16(34, 0, true);
      centralView.setUint16(36, 0, true);
      centralView.setUint32(38, 0, true);
      centralView.setUint32(42, offset, true);
      centralHeader.set(nameBytes, 46);

      centralParts.push(centralHeader);
      offset += localHeader.length + dataBytes.length;
    }

    const centralDirectoryOffset = offset;
    const centralDirectorySize = centralParts.reduce((sum, part) => sum + part.length, 0);

    const end = new Uint8Array(22);
    const endView = new DataView(end.buffer);
    endView.setUint32(0, 0x06054b50, true);
    endView.setUint16(4, 0, true);
    endView.setUint16(6, 0, true);
    endView.setUint16(8, centralParts.length, true);
    endView.setUint16(10, centralParts.length, true);
    endView.setUint32(12, centralDirectorySize, true);
    endView.setUint32(16, centralDirectoryOffset, true);
    endView.setUint16(20, 0, true);

    return this.concat([...localParts, ...centralParts, end]);
  }

  private concat(parts: Uint8Array[]): Uint8Array {
    const length = parts.reduce((sum, part) => sum + part.length, 0);
    const result = new Uint8Array(length);
    let offset = 0;

    for (const part of parts) {
      result.set(part, offset);
      offset += part.length;
    }

    return result;
  }

  private crc32(bytes: Uint8Array): number {
    let crc = 0xffffffff;

    for (const byte of bytes) {
      crc ^= byte;
      for (let bit = 0; bit < 8; bit++) {
        crc = crc & 1 ? (crc >>> 1) ^ 0xedb88320 : crc >>> 1;
      }
    }

    return (crc ^ 0xffffffff) >>> 0;
  }

  private columnName(columnNumber: number): string {
    let name = '';
    let current = columnNumber;

    while (current > 0) {
      const remainder = (current - 1) % 26;
      name = String.fromCharCode(65 + remainder) + name;
      current = Math.floor((current - 1) / 26);
    }

    return name;
  }

  private normalizeCellText(value: string): string {
    return value.replace(/\s+/g, ' ').trim();
  }

  private normalizeSheetName(sheetName: string): string {
    const normalized = sheetName.replace(/[\[\]:*?/\\]/g, ' ').trim();
    return (normalized || 'Sheet1').slice(0, 31);
  }

  private normalizeFileName(fileName: string): string {
    return fileName.toLowerCase().endsWith('.xlsx') ? fileName : `${fileName}.xlsx`;
  }

  private xmlEscape(value: string): string {
    return value
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&apos;');
  }

  private download(bytes: Uint8Array, fileName: string): void {
    const blob = new Blob([bytes as BlobPart], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    });

    const url = window.URL.createObjectURL(blob);
    const anchor = document.createElement('a');

    anchor.href = url;
    anchor.download = fileName;
    anchor.click();

    window.URL.revokeObjectURL(url);
  }
}
