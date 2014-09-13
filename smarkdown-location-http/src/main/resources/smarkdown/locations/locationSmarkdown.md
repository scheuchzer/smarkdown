# Location - Smarkdown


Smarkdown servers can import files from other smarkdown servers. The referenced
smarkdown servers are accessed through ``HTTP`` or ``HTTPS``.


## Configuration


### Enable Smarkdown HTTP(S) lookups

The configuration is the same as with [HTTP(S) Locaitons](locationHttp.md) except the protocol name
that is ``smarkdown:http://`` and ``smarkdown:https://`` instead of ``http://`` and ``https://``.

Syntax:
```bash
smarkdown:http:{path-to-root}
smarkdown:https:{path-to-root}
```


for example:
```json
{"url": "smarkdown:http://example.com/smarkdown"}
``` 

In this example the server will load the smarkdown listing file from ``http://example.com/smarkdown/listing.json``.
 