#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["spell","Fulminance","projectile","justsummoned"]}
data modify entity @e[tag=justsummoned,tag=projectile,tag=Fulminance,limit=1] data.UUID set from entity @s data.UUID
scoreboard players operation @e[tag=justsummoned,tag=projectile,tag=Fulminance,limit=1] m.spell.power = @s m.spell.power
execute as @e[tag=Fulminance,tag=projectile,tag=justsummoned] run function spells:fulminance/__generated__/block/531