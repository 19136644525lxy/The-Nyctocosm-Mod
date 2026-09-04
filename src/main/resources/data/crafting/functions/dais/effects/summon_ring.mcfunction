#built using mc-build (https://github.com/mc-build/mc-build)

summon armor_stand ~ ~ ~ {Pose:{Head:[0f,0.0001f,0f]},HasVisualFire:1b,Marker:1b,Invisible:1b,Tags:["justsummoned","ring","glyph","dais","pos"],ArmorItems:[{},{},{},{id:"minecraft:quartz",Count:1b,tag:{CustomModelData:7}}]}
summon armor_stand ~ ~ ~ {Pose:{Head:[0f,0.0001f,0f]},HasVisualFire:1b,Marker:1b,Invisible:1b,Tags:["justsummoned","ring","glyph","dais","up"],ArmorItems:[{},{},{},{id:"minecraft:quartz",Count:1b,tag:{CustomModelData:7}}]}
summon armor_stand ~ ~ ~ {Pose:{Head:[0f,0.0001f,0f]},HasVisualFire:1b,Marker:1b,Invisible:1b,Tags:["justsummoned","ring","glyph","dais","neg"],ArmorItems:[{},{},{},{id:"minecraft:quartz",Count:1b,tag:{CustomModelData:7}}]}
summon armor_stand ~ ~ ~ {Pose:{Head:[0f,0.0001f,0f]},HasVisualFire:1b,Marker:1b,Invisible:1b,Tags:["justsummoned","ring","dais"],ArmorItems:[{},{},{},{id:"minecraft:quartz",Count:1b,tag:{CustomModelData:8}}]}
execute as @e[tag=justsummoned,tag=ring] at @s run function crafting:dais/effects/__generated__/block/27