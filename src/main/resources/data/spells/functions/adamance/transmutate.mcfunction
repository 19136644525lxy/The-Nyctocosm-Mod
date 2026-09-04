#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["spell","Adamance","rooter","justsummoned"]}
data modify entity @e[tag=justsummoned,tag=rooter,tag=Adamance,limit=1] data.UUID set from entity @s data.UUID
scoreboard players operation @e[tag=justsummoned,tag=rooter,tag=Adamance,limit=1] m.spell.power = @s m.spell.power
execute as @e[tag=Adamance,tag=justsummoned] run function spells:adamance/__generated__/block/422