#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned ~ ~1 ~ run particle flash ~ ~ ~ 0.1 0.1 0.1 0 5 force
execute positioned ~ ~0.2 ~ run particle minecraft:block ice ~ ~ ~ 0.3 0.2 0.3 1 100
execute at @s positioned ^-0.7 ^ ^0.85 align y run function animated_java:ajnullpoint/summon
execute as @e[tag=aj.ajnullpoint.root] at @s run function sorcerer:__generated__/block/185
execute as @a[distance=..4] run function sorcerer:__generated__/block/186
execute at @e[tag=aj.ajnullpoint.root,limit=1] run summon wither_skeleton ~ ~-100 ~ {NoGravity:1b,Silent:1b,DeathLootTable:"minecraft:empty",PersistenceRequired:1b,NoAI:1b,CanPickUpLoot:0b,Health:1000000f,Tags:["sorcererstaffhitbox","sorcerer","invul","hitbox","justsummoned"],ActiveEffects:[{Id:14,Amplifier:1b,Duration:20000000,ShowParticles:0b}],Attributes:[{Name:generic.max_health,Base:1000000}]}
execute as @e[tag=sorcererstaffhitbox,tag=justsummoned] at @s run function sorcerer:__generated__/block/192
tag @e[tag=sorcererhitbox] add invul
scoreboard players operation Nullpoint ns.health = NullpointMaxHealth ns.health
schedule function sorcerer:__generated__/sequence/5 200t replace