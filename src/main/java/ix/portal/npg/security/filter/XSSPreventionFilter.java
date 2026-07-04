package ix.portal.npg.security.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.*;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Hossein Sadeghi on 11/25/2017.
 */
@Component
@Order(-99999)
public class XSSPreventionFilter implements Filter {

    class XSSRequestWrapper extends HttpServletRequestWrapper {

        private final String body;
        private ObjectMapper objectMapper = new ObjectMapper();

        public XSSRequestWrapper(HttpServletRequest request) throws IOException {
            // So that other request method behave just like before
            super(request);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;
            try {
                InputStream inputStream = request.getInputStream();
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int bytesRead = -1;
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                } else {
                    stringBuilder.append("");
                }
            } catch (IOException ex) {
                throw ex;
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        throw ex;
                    }
                }
            }
            // Store request body content in 'requestBody' variable
            String requestBody = stringBuilder.toString();
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            //TODO -- Update your request body here
            /*if (!(jsonNode instanceof MissingNode)) {
                ObjectNode objectNode = ((ObjectNode) jsonNode);
                objectNode
                    .fieldNames()
                    .forEachRemaining(
                        key -> {
                            //System.out.println("objectNode.get(s) = " + objectNode.get(key));
                            String value = stripXSS(objectNode.get(key).asText());
                            ((ObjectNode) jsonNode).put(key, value);
                        }
                    );
            }*/

            if (!(jsonNode instanceof MissingNode)) {
                ObjectNode objectNode = ((ObjectNode) jsonNode);
                objectNode
                    .fieldNames()
                    .forEachRemaining(
                        key -> {
                            if (objectNode.get(key) instanceof ObjectNode) {
                                ObjectNode objectNodeL2 = ((ObjectNode) objectNode.get(key));
                                objectNodeL2
                                    .fieldNames()
                                    .forEachRemaining(
                                        key2 -> {
                                            if (objectNodeL2.get(key2) instanceof ObjectNode) {
                                                ObjectNode objectNodeL3 = ((ObjectNode) objectNodeL2.get(key2));
                                                objectNodeL3
                                                    .fieldNames()
                                                    .forEachRemaining(
                                                        key3 -> {
                                                            if (objectNodeL3.get(key3) instanceof ObjectNode) {
                                                                ObjectNode objectNodeL4 = ((ObjectNode) objectNodeL3.get(key3));
                                                                objectNodeL4
                                                                    .fieldNames()
                                                                    .forEachRemaining(
                                                                        key4 -> {
                                                                            //String value4 = objDataModification.doDataModification(objectNodeL4.get(key4) instanceof NullNode ? null : objectNodeL4.get(key4).asText() );
                                                                            //((ObjectNode) objectNodeL4).put(key4, value4);
                                                                            if (objectNodeL4.get(key4).isInt()) {
                                                                                Integer valueClean = objectNodeL4.get(key4).asInt();
                                                                                ((ObjectNode) objectNodeL4).put(key4, valueClean);
                                                                            } else if (objectNodeL4.get(key4).isBoolean()) {
                                                                                Boolean valueClean = objectNodeL4.get(key4).asBoolean();
                                                                                ((ObjectNode) objectNodeL4).put(key4, valueClean);
                                                                            } else if (
                                                                                objectNodeL4.get(key4).isDouble() ||
                                                                                objectNodeL4.get(key4).isFloat()
                                                                            ) {
                                                                                Double valueClean = objectNodeL4.get(key4).asDouble();
                                                                                ((ObjectNode) objectNodeL4).put(key4, valueClean);
                                                                            } else if (objectNodeL4.get(key4).isLong()) {
                                                                                Long valueClean = objectNodeL4.get(key4).asLong();
                                                                                ((ObjectNode) objectNodeL4).put(key4, valueClean);
                                                                            } else if (objectNodeL4.get(key4).isNull()) {
                                                                                ((ObjectNode) objectNodeL4).put(key4, (JsonNode) null);
                                                                            } else if (objectNodeL4.get(key4).isArray()) {
                                                                                ArrayNode valueClean = (ArrayNode) objectNodeL4.get(key4);
                                                                                ArrayNode cleanNode = new ArrayNode(
                                                                                    JsonNodeFactory.instance
                                                                                );
                                                                                valueClean.forEach(
                                                                                    nv -> {
                                                                                        cleanNode.add(stripXSS(nv.asText()));
                                                                                    }
                                                                                );
                                                                                ((ObjectNode) objectNodeL4).put(key4, cleanNode);
                                                                            } else {
                                                                                String valueClean = stripXSS(
                                                                                    objectNodeL4.get(key4).asText()
                                                                                );
                                                                                ((ObjectNode) objectNodeL4).put(key4, valueClean);
                                                                            }
                                                                        }
                                                                    );
                                                            } else {
                                                                //String value3 = objDataModification.doDataModification(objectNodeL3.get(key3) instanceof NullNode ? null : objectNodeL3.get(key3).asText());
                                                                //((ObjectNode) objectNodeL3).put(key3, value3);
                                                                if (objectNodeL3.get(key3).isInt()) {
                                                                    Integer valueClean = objectNodeL3.get(key3).asInt();
                                                                    ((ObjectNode) objectNodeL3).put(key3, valueClean);
                                                                } else if (objectNodeL3.get(key3).isBoolean()) {
                                                                    Boolean valueClean = objectNodeL3.get(key3).asBoolean();
                                                                    ((ObjectNode) objectNodeL3).put(key3, valueClean);
                                                                } else if (
                                                                    objectNodeL3.get(key3).isDouble() || objectNodeL3.get(key3).isFloat()
                                                                ) {
                                                                    Double valueClean = objectNodeL3.get(key3).asDouble();
                                                                    ((ObjectNode) objectNodeL3).put(key3, valueClean);
                                                                } else if (objectNodeL3.get(key3).isLong()) {
                                                                    Long valueClean = objectNodeL3.get(key3).asLong();
                                                                    ((ObjectNode) objectNodeL3).put(key3, valueClean);
                                                                } else if (objectNodeL3.get(key3).isNull()) {
                                                                    ((ObjectNode) objectNodeL3).put(key3, (JsonNode) null);
                                                                } else if (objectNodeL3.get(key3).isArray()) {
                                                                    ArrayNode valueClean = (ArrayNode) objectNodeL3.get(key3);
                                                                    ArrayNode cleanNode = new ArrayNode(JsonNodeFactory.instance);
                                                                    valueClean.forEach(
                                                                        nv -> {
                                                                            cleanNode.add(stripXSS(nv.asText()));
                                                                        }
                                                                    );
                                                                    ((ObjectNode) objectNodeL3).put(key3, cleanNode);
                                                                } else {
                                                                    String valueClean = stripXSS(objectNodeL3.get(key3).asText());
                                                                    ((ObjectNode) objectNodeL3).put(key3, valueClean);
                                                                }
                                                            }
                                                        }
                                                    );
                                            } else {
                                                //String value2 = objDataModification.doDataModification(objectNodeL2.get(key2) instanceof NullNode ? null : objectNodeL2.get(key2).asText());
                                                //((ObjectNode) objectNodeL2).put(key2, value2);
                                                if (objectNodeL2.get(key2).isInt()) {
                                                    Integer valueClean = objectNodeL2.get(key2).asInt();
                                                    ((ObjectNode) objectNodeL2).put(key2, valueClean);
                                                } else if (objectNodeL2.get(key2).isBoolean()) {
                                                    Boolean valueClean = objectNodeL2.get(key2).asBoolean();
                                                    ((ObjectNode) objectNodeL2).put(key2, valueClean);
                                                } else if (objectNodeL2.get(key2).isDouble() || objectNodeL2.get(key2).isFloat()) {
                                                    Double valueClean = objectNodeL2.get(key2).asDouble();
                                                    ((ObjectNode) objectNodeL2).put(key2, valueClean);
                                                } else if (objectNodeL2.get(key2).isLong()) {
                                                    Long valueClean = objectNodeL2.get(key2).asLong();
                                                    ((ObjectNode) objectNodeL2).put(key2, valueClean);
                                                } else if (objectNodeL2.get(key2).isNull()) {
                                                    ((ObjectNode) objectNodeL2).put(key2, (JsonNode) null);
                                                } else if (objectNodeL2.get(key2).isArray()) {
                                                    ArrayNode valueClean = (ArrayNode) objectNodeL2.get(key2);
                                                    ArrayNode cleanNode = new ArrayNode(JsonNodeFactory.instance);
                                                    valueClean.forEach(
                                                        nv -> {
                                                            cleanNode.add(stripXSS(nv.asText()));
                                                        }
                                                    );
                                                    ((ObjectNode) objectNodeL2).put(key2, cleanNode);
                                                } else {
                                                    String valueClean = stripXSS(objectNodeL2.get(key2).asText());
                                                    ((ObjectNode) objectNodeL2).put(key2, valueClean);
                                                }
                                            }
                                        }
                                    );
                                ((ObjectNode) jsonNode).put(key, objectNodeL2);
                            } else {
                                //cleanValue((ObjectNode) jsonNode, key);
                                //String value = objDataModification.doDataModification(objectNode.get(key) instanceof NullNode ? null : objectNode.get(key).asText());
                                //((ObjectNode) jsonNode).put(key, value);
                                if (jsonNode.get(key).isInt()) {
                                    Integer valueClean = jsonNode.get(key).asInt();
                                    ((ObjectNode) jsonNode).put(key, valueClean);
                                } else if (jsonNode.get(key).isBoolean()) {
                                    Boolean valueClean = jsonNode.get(key).asBoolean();
                                    ((ObjectNode) jsonNode).put(key, valueClean);
                                } else if (jsonNode.get(key).isDouble() || jsonNode.get(key).isFloat()) {
                                    Double valueClean = jsonNode.get(key).asDouble();
                                    ((ObjectNode) jsonNode).put(key, valueClean);
                                } else if (jsonNode.get(key).isLong()) {
                                    Long valueClean = jsonNode.get(key).asLong();
                                    ((ObjectNode) jsonNode).put(key, valueClean);
                                } else if (jsonNode.get(key).isNull()) {
                                    ((ObjectNode) jsonNode).put(key, (JsonNode) null);
                                } else if (jsonNode.get(key).isArray()) {
                                    ArrayNode valueClean = (ArrayNode) jsonNode.get(key);
                                    ArrayNode cleanNode = new ArrayNode(JsonNodeFactory.instance);
                                    valueClean.forEach(
                                        nv -> {
                                            cleanNode.add(stripXSS(nv.asText()));
                                        }
                                    );
                                    ((ObjectNode) jsonNode).put(key, cleanNode);
                                } else {
                                    String valueClean = stripXSS(jsonNode.get(key).asText());
                                    ((ObjectNode) jsonNode).put(key, valueClean);
                                }
                            }
                        }
                    );
            }

            // Finally store updated request body content in 'body' variable
            body = jsonNode.toString();
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
            ServletInputStream servletInputStream = new ServletInputStream() {
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener listener) {}
            };
            return servletInputStream;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }

        /**
         * Removes all the potentially malicious characters from a string
         * @param value the raw string
         * @return the sanitized string
         */
        private String stripXSS(String value) {
            String cleanValue = null;
            if (value != null) {
                cleanValue = Normalizer.normalize(value, Normalizer.Form.NFD);

                // Avoid null characters
                cleanValue = cleanValue.replaceAll("\0", "");

                // Avoid anything between script tags
                Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
                cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

                // Avoid anything in a src='...' type of expression
                scriptPattern =
                    Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

                scriptPattern =
                    Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

                // Remove any lonesome </script> tag
                scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
                cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

                // Remove any lonesome <script ...> tag
                scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

                // Avoid eval(...) expressions
                scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

                // Avoid expression(...) expressions
                scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

                // Avoid javascript:... expressions
                scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
                cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

                // Avoid vbscript:... expressions
                scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
                cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

                // Avoid onload= expressions
                scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
            }
            return cleanValue;
        }
    }

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        XSSRequestWrapper wrapper = new XSSRequestWrapper((HttpServletRequest) request);
        chain.doFilter(wrapper, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
}


