# Empty forms / broken UI — diagnosis and fixes

**Date:** 2026-07-12  
**Branch:** `cursor/full-upgrade-audit-eec2`

## Symptom

DB has rows, but entity forms show no data; form layout looks broken.

## Root causes addressed in code

| Cause | Effect | Fix |
|-------|--------|-----|
| JWT valid but `SecurityCache` empty (app restart) | API list calls return **401** `error.npg.token.empty`; lists stay blank | `JWTFilter` rehydrates session from valid JWT |
| Permission match only on `resource.name` | Menus/cards hidden or API **403** when only `resourceName` is populated | Fallback to `resourceName` in directive, route guard, and `CustomSecuredAuthorizationManager` |
| Mapper omitted flat `resourceName` | Fragile permission payloads | MapStruct now fills `resourceId` / `resourceName` / `resourceDisplayName` |
| Portability / Event Log / File Report search-first | Empty table until user clicks Search | Auto-load first page on `ngOnInit` |
| Auto dark theme (OS) + partial Material tokens | Forms look broken / low contrast | Default theme = **light** unless user toggles |
| Navbar `.dropdown-item { background:#000 }` | Black menus over theme tokens | Use theme tokens |
| List `onError()` left arrays `undefined` | Neither table nor “not found” rendered | Set collections to `[]` |

## What you must do locally

1. Checkout PR tip: `cursor/full-upgrade-audit-eec2` (not an old `npg-portal-instance-security` copy).
2. Rebuild backend + frontend; hard refresh browser (Ctrl+F5).
3. Log out and log in once (or just reload after this fix — session rehydrate should work).
4. In DevTools → Network, open a form and check:

| Request | Healthy |
|---------|---------|
| `GET /api/account` | **200**, `resourceAuthorities` non-empty (or user is `ROLE_ADMIN`) |
| `GET /api/settings?...` (or entity API) | **200** + JSON array; not 401/403 |
| Response body | Rows present; `X-Total-Count` > 0 |

5. If API is **200** with `[]` but SQL shows rows → app is connected to a **different** schema/DB than the one you inspected.
6. If API is **403** → assign `VIEW` in `JHI_RESOURCE_AUTHORITY` for the user’s role.
7. If UI still dark/broken → clear `localStorage['npg-portal-theme']` or click the theme toggle to Light.
