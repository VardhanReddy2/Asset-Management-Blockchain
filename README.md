# Asset Management Blockchain CLI Application

> A simple blockchain application for asset management for demonstration and academic purposes.

## Dependencies:

- bouncy castle
    - Can be import into maven if IDE is used
    - Need to download the bouncy castle jar into <java installation folder>/jre/lib/ext

## Features:

- Assets can be added by the manager (unlimited privilages and all the users & their transactions are visible to it)
- Zero Knowledge Proof is implement in two ways
    - Having a unique private key (kind of transaction password)
    - Applying ECDSA Signatures using the user specific system generated private key (this is different from the above private key)
- Blocks are created when any new user registers
- Hashes are further encrypted using DES algorithm
- All the details about the blockchain will be saved into files using serialization method


### References:
[CryptoKass - NoobChain-Tutorial-Part-2]

[CryptoKass - NoobChain-Tutorial-Part-2]:  <https://github.com/CryptoKass/NoobChain-Tutorial-Part-2>