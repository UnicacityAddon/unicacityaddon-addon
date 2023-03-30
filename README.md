# UnicacityAddon

![Build](https://github.com/rettichlp/unicacityaddon-addon/actions/workflows/build.yml/badge.svg)
![Release](https://github.com/rettichlp/unicacityaddon-addon/actions/workflows/release.yml/badge.svg)
[![Discord](https://discord.com/api/guilds/1008928645185810463/widget.png)](https://discord.gg/A9u5eY7CbS)

* [Basic Overview](#basic-overview)
  * [Supported Versions](#supported-versions)
    * [LabyMod 3](#labymod-3)
    * [LabyMod 4](#labymod-4)
* [Installation](#installation)
  * [Release](#release)
  * [Development versions and manual installation](#development-versions-and-manual-installation)
* [Contribute](#contribute)

## Basic Overview

UnicacityAddon is a LabyMod addon which is written for the Minecraft server Unicacity and provides
specialized, nice-to-have features and utilities for everyday gameplay.

At the time of the project idea, Unicacity supported Minecraft versions 1.12.2 up to 1.16.5. There
was already a Minecraft Forge mod called [UCUtils](https://github.com/paulzhng/UCUtils), but it only
supported Minecraft version 1.12.<br>
One day it was decided to update Unicacity to 1.16.5+, so there was no more modification. @Dimiikou
and I decided to program our own modification, which is based on the mod, which is no longer being
developed. We decided to develop a LabyMod addon because there are already ready-made functions that
we can use.

During the development of the [UnicacityAddon](https://github.com/rettichlp/UnicacityAddon) LabyMod
3 addon for Minecraft version 1.16.5, the web API endpoint of
[UCUtils](https://github.com/paulzhng/UCUtils) was switched off. From now on, the modification was
unusable and a replacement was sought. We took our chance and let our LabyMod 1.16.5 addon rest and
developed one for Minecraft version 1.12.2. The functions of the mod were taken over and improved in
the addon. After a short time we were able to publish it and quickly reached many users. Since then,
new features and improvements have been added all the time, allowing for a more enjoyable and easier
gaming experience. That was the start of the UnicacityAddon.

A few months later, LabyMod 4 was announced. With Unicacity still due to be updated to 1.16.5+, this
was an opportune moment to kill two birds with one stone. On the one hand the update to LabyMod 4
and on the other hand the support for Minecraft version 1.16.5+.

### Supported Versions

> In LabyMod 3 there was no command implementation, so there was used Minecraft Forge for this. In LabyMod 4 is an own command implementation so Minecraft Forge isn't longer needed.

#### LabyMod 3

Addon releases: `v1.0.0` - `v1.9.2`<br>
Requires Minecraft Forge: ✔️<br>
Supported Minecraft versions:
- `1.12.2`

#### LabyMod 4

Addon releases: `v2.0.0` - `latest`<br>
Requires Minecraft Forge: ❌<br>
Supported Minecraft versions:
- `1.12.2`
- `1.16.5`
- `1.17.1`
- `1.18.2`
- `1.19.2`
- `1.19.3`
- `1.19.4`

## Installation

### Release

A release is directly pushed to the ingame addon store from LabyMod
(using [Flint](https://flintmc.net/)). The update will then be installed automatically.

### Development versions and manual installation

The addon can also be installed manually. All you have to do is download it and put the `.jar` file
in the `%appdata%/.minecraft/labymod-neo/addons` folder.

## Contribute

1. Clone the repository with `git clone https://github.com/rettichlp/unicacityaddon-addon.git`
2. Open the downloaded folder as IntelliJ Project
3. IntelliJ will import all necessary stuff
4. Fork this repository
5. Make your code changes and push those to your forked repository
6. Create a pull request

> Read our [Contributing guidelines](https://github.com/rettichlp/unicacityaddon-addon/blob/main/CONTRIBUTING.md)
