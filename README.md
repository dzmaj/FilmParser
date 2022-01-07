# FilmParser
This is a program for parsing .m2rec recording files for the game Myth II Soulblighter.
The file was reverse engineered using the game client and and a hex editor, and as such there are still portions of the file format that are a mystery or are read incorrectly.
## Features
- Parses directories of recordings
- Outputs each recording to a separate text file
- Parses: name, mesh tag ID, game build, plugin list, player list, packet list
- Chat packets are decoded and mapped to the sender along with timestamp and whether the message was whispered or yelled
- Other packets can be optionally logged with the timestamp, sender, and the raw data
- Plugin download links are retrieved from http://tain.totalcodex.net using the api used by the Myth II client
## Usage
- Build to .jar or download prebuilt
- ``java -jar FilmParser.jar``
- Select option to log all packets or only chat messages
- Enter path to directory containing all recordings to be processed
## Notes and Issues
- Issue: some packets are processed incorrectly
- TODO: figure out all the rest of the packet types and formats
