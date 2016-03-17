MineCraft Server RPG Overhaul - Bukkit Plugins
=

This was a project started up in summer of 2013.  The idea was to use the Bukkit server and create plugins to make an epic RPG experience such as those are known from World of Warcraft, Guild Wars, and other MMOs.

Many features were completed for this project to recreate those expereinces.  The code located in this repository represents my contribution to this project.  I was the main developer behind the project at the time.

## Plugin Breakdowns

### BankManager
This plugin allowed banks to created within the world.  They all had a universal inventory for each player.  The idea being a player may travel between towns and access the bank at their given location and retrieve or deposit items.

### PartyCreator
Here is a plugin that brought that good ol' parting system into minecraft allows users to group up and adventure together.

### RollerCore
A dice rolling plugin which ties into several of our other plugins such as the PartyCreator plugin to offer the ability to roll/pass on items depending on the rarity of the item on a boss or random mob drop.

### RPGAPI
A generalized package of useful features that can be used within the other projects.  The main feature at the time was a menu system made using Minecraft maps.

### RPGBossBars
A simple plugin which tied into "EpicBossesRecoded" which created a visisble and modifiable bar above all bosses and further expanded to all mobs.

### RPGItemCreator
This was used to generate the items that were dropped from mobs.  This includes minimum level to use the item, item lore, item type, soulbound (None, Equip, On-Pickup), and further stats.

### RPGQuest
One of the larger features which allows the creation off quests in-game with the ability to create mobs to offer the quests while overwriting default behavior (So they don't attack and move).  This also had teh ability for quest progression, you must of completed some quest for the next quest giver to appear.  Quests also had reqrds such as exp and items.

### RPGStats
This is probably the main feature which most of everything revolved around.  This gives players and items stats.  Player stats include health, defense, min/max dmg, resistances (water/fire/lightning/holy/etc), armour, crit, etc.  Items also having the ability to increase these stats with bonuses of their own.

### RPGTeleport
Allows players to randomly teleport to random locations on the map or specific named zones.

### TradeCommander
Every MMO needs a trading system.  This allows players to trade with each other as long as they are within a certain distance from each other.  This also opens a UI (inventory) for both players allowing them to see eachothers trades.  A split down the middle only allows each player access to their own half.  Both players must also agree to the trade and removing/adding items resets the trade acceptance for both players.
