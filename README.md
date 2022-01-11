# FilmParser
This is a program for parsing .m2rec recording files for the game Myth II Soulblighter.
The file was reverse engineered using the game client and and a hex editor, and as such there are still portions of the file format that are a mystery or are read incorrectly.
## Features
- Bulk processes directories of recordings
- Outputs each recording to a separate text file
- Parses: name, mesh tag ID, game build, plugin list, player list, packet list
- All packets can be logged with timestamps, type (if known), sender, and the raw data
- Chat packets are decoded to display the message and whether it was whispered or yelled
- Movement packets are decoded to display the formation, unit IDs, destination/waypoints, and facing
- General Action packets display the unit IDs and command subtype (stop, scatter, retreat, special ability, guard, or taunt)
- Attack target commands display unit IDs for attackers and targets
- Plugin download links are retrieved from http://tain.totalcodex.net using the api used by the Myth II client
## Usage
- Requires java 17
- Build to .jar or download prebuilt
- ``java -jar FilmParser.jar``
- Select options on which packets to log
- Enter path to directory containing all recordings to be processed
## Notes and Issues
- Issue: some packets are processed incorrectly
- TODO: figure out all the rest of the packet types and formats
