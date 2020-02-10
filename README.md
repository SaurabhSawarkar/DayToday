#ReadMe file for DayToday test app

Limitations:

1. Skipping object_ids and comments while receiving response from the server. These contains the
dynamic key and to parse them we need to provide custom implementation of Gson parser.
As this is not being used on UI, it is skipped as of now.

2. Realm database is encrypted with static key. Key handling is not upto the mark for the POC. Key 
should be also be ecrypted using AES or similar also and should be kept safe.

3. Realm database is not encrypted for debug builds. This helps viewing it in browser easier.