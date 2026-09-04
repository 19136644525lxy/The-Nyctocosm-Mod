#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["spell","Reflectance","reflector","justsummoned"]}
data modify entity @e[tag=justsummoned,tag=reflector,tag=Reflectance,limit=1] data.UUID set from entity @s data.UUID
scoreboard players operation @e[tag=justsummoned,tag=reflector,tag=Reflectance,limit=1] m.spell.power = @s m.spell.power
execute as @e[tag=Reflectance,tag=justsummoned] run function spells:reflectance/__generated__/block/384