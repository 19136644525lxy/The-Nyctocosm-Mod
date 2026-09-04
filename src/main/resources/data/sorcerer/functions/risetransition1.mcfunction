#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=aj.ajsorcererm2.bone.group,limit=1] at @s rotated 0 0 run function sorcerer:__generated__/block/193
function animated_java:ajsorcererm2/apply_variant/staffless
execute at @e[tag=aj.ajnullpoint.root,limit=1] run summon wither_skeleton ~ ~-100 ~ {NoGravity:1b,Silent:1b,DeathLootTable:"minecraft:empty",PersistenceRequired:1b,NoAI:1b,CanPickUpLoot:0b,Health:1000000f,Tags:["sorcererstaffhitbox","invul","sorcerer","hitbox","justsummoned"],ActiveEffects:[{Id:14,Amplifier:1b,Duration:20000000,ShowParticles:0b}],Attributes:[{Name:generic.max_health,Base:1000000}]}
execute as @e[tag=sorcererstaffhitbox,tag=justsummoned] at @s run function sorcerer:__generated__/block/201
tag @e[tag=sorcererhitbox] add invul
scoreboard players operation Nullpoint ns.health = NullpointMaxHealth ns.health
schedule function sorcerer:__generated__/sequence/7 140t replace