#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["spell","Gravitance","projectile","justsummoned"]}
data modify entity @e[tag=justsummoned,tag=projectile,tag=Gravitance,limit=1] data.UUID set from entity @s data.UUID
scoreboard players operation @e[tag=justsummoned,tag=projectile,tag=Gravitance,limit=1] m.spell.power = @s m.spell.power
execute as @e[tag=justsummoned,tag=projectile,tag=Gravitance] rotated ~ ~ run function spells:gravitance/__generated__/block/349