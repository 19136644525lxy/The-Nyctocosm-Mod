#built using mc-build (https://github.com/mc-build/mc-build)

summon armor_stand ~ ~ ~ {Pose:{Head:[0f,0.0001f,0f]},HasVisualFire:1b,Marker:1b,Invisible:1b,Tags:["justsummoned","anvil","dais"],ArmorItems:[{},{},{},{id:"minecraft:quartz",Count:1b,tag:{CustomModelData:9}}]}
execute as @e[tag=justsummoned,tag=anvil] at @s run function crafting:dais/effects/__generated__/block/28