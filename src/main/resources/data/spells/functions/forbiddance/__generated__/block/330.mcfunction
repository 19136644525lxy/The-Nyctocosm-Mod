#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["spell","Forbiddance","projectile","justsummoned"]}
data modify entity @e[tag=justsummoned,tag=projectile,tag=Forbiddance,limit=1] data.UUID set from entity @s data.UUID
scoreboard players operation @e[tag=justsummoned,tag=projectile,tag=Forbiddance,limit=1] m.spell.power = @s m.spell.power
execute as @e[tag=justsummoned,tag=projectile,tag=Forbiddance] rotated ~ ~ run function spells:forbiddance/__generated__/block/331