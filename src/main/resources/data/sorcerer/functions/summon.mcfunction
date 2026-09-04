#built using mc-build (https://github.com/mc-build/mc-build)

execute rotated ~ 0 run function animated_java:ajsorcerer/summon/staffless
execute as @e[tag=aj.ajsorcerer.root] at @s run function sorcerer:__generated__/block/3
summon wither_skeleton ~ ~-100 ~ {NoGravity:1b,Silent:1b,DeathLootTable:"minecraft:empty",PersistenceRequired:1b,NoAI:1b,CanPickUpLoot:0b,Health:1000000f,Tags:["sorcererhitbox","sorcerer","hitbox","invul","justsummoned"],ActiveEffects:[{Id:14,Amplifier:1b,Duration:20000000,ShowParticles:0b}],Attributes:[{Name:generic.max_health,Base:1000000}]}
execute as @e[tag=sorcererhitbox,tag=justsummoned] run function sorcerer:__generated__/block/4
scoreboard players operation Sorcerer ns.health = SorcererMaxHealth ns.health
scoreboard players set Sorcerer ns.phase 1
scoreboard players set Sorcerer ns.attacking 2
execute store result bossbar minecraft:sorcerer max run scoreboard players get SorcererMaxHealth ns.health
execute store result bossbar minecraft:sorcerer value run scoreboard players get Sorcerer ns.health