#built using mc-build (https://github.com/mc-build/mc-build)

summon armor_stand ~ ~-100 ~ {Silent:1b,Invulnerable:1b,HasVisualFire:1b,Glowing:1b,Marker:1b,Invisible:1b,PersistenceRequired:1b,Tags:["resonance","effect","element","justsummoned"],ArmorItems:[{},{},{},{id:"minecraft:quartz",Count:1b,tag:{CustomModelData:3}}]}
particle dust 0.000 1.000 0.867 1 ~ ~1.5 ~ .5 .5 .5 0 70 normal
execute as @e[tag=resonance,tag=justsummoned] run function effects:elements/__generated__/block/88