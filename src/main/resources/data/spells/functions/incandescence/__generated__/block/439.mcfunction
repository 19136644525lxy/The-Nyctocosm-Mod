#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["spell","Incandescence","projectile","justsummoned"]}
data modify entity @e[tag=justsummoned,tag=projectile,tag=Incandescence,limit=1] data.UUID set from entity @s data.UUID
scoreboard players operation @e[tag=justsummoned,tag=projectile,tag=Incandescence,limit=1] m.spell.power = @s m.spell.power
execute as @e[tag=Incandescence,tag=projectile,tag=justsummoned] run function spells:incandescence/__generated__/block/440
summon marker ~ ~ ~ {Tags:["spell","Incandescence","particle_emitter","justsummoned"]}
data modify entity @e[tag=justsummoned,tag=particle_emitter,tag=Incandescence,limit=1] data.UUID set from entity @s data.UUID
execute as @e[tag=Incandescence,tag=particle_emitter,tag=justsummoned] run function spells:incandescence/__generated__/block/441