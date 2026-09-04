#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard objectives add ns.halobeamtimer dummy
scoreboard objectives add ns.halobeamrange dummy
scoreboard objectives add ns.shardhailtimer dummy
scoreboard objectives add ns.icespikestimer dummy
scoreboard objectives add ns.singularitychargetimer dummy
scoreboard objectives add ns.frozenbladestimer dummy
scoreboard objectives add ns.voidmeteortimer dummy
scoreboard objectives add ns.voidbeamrange dummy
scoreboard objectives add ns.seekingsingularitiestimer dummy
scoreboard objectives add ns.health dummy
scoreboard objectives add ns.damagetaken dummy
scoreboard objectives add ns.phase dummy
scoreboard objectives add ns.attack dummy
scoreboard objectives add ns.attacking dummy
scoreboard objectives add ns.attackcooldown dummy
scoreboard players set plungedamage value 20
scoreboard players set halobeamdamage value 10
scoreboard players set sharddamage value 10
scoreboard players set dashdamage value 15
scoreboard players set voidwavedamage value 20
scoreboard players set icespikesdamage value 3
scoreboard players set singularitychargedamage value 20
scoreboard players set javelindamage value 16
scoreboard players set frozenbladesdamage value 14
scoreboard players set airdashdamage value 15
scoreboard players set voidmeteordamage value 15
scoreboard players set voidbeamdamage value 3
scoreboard players set seekingsingularitiesdamage value 13
scoreboard players set SorcererMaxHealth ns.health 1000
scoreboard players set NullpointMaxHealth ns.health 300
bossbar add sorcerer {"translate":"entity.nyctocosm.bossbar","color":"gold","with":[{"translate":"entity.nyctocosm.bossbar.sorcerer","color":"dark_purple"}]}
bossbar set minecraft:sorcerer max 1000
bossbar set minecraft:sorcerer color purple
bossbar set minecraft:sorcerer style notched_6