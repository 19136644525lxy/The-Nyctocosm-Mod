#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add walker
tag @s add root
tag @s add custom
scoreboard players set @s timer 0
tag @s remove justsummoned
function nyctocosm:id/assign
summon giant ~ ~ ~ {ActiveEffects:[{Id:14,Amplifier:1b,Duration:20000000,ShowParticles:0b}],PersistenceRequired:1b,Silent:1b,Tags:["custom","walker","hitbox","justsummoned"]}
scoreboard players operation @e[tag=hitbox,tag=walker,tag=justsummoned,limit=1] id = @s id
tp @e[tag=hitbox,tag=walker,tag=justsummoned,limit=1] @s
tag @e[tag=walker,tag=hitbox,tag=justsummoned] remove justsummoned
function nyctocosm:mobs/death_walker/state/idle