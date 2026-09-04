#built using mc-build (https://github.com/mc-build/mc-build)

summon armor_stand ~ ~10 ~ {Silent:1b,Marker:1b,Invisible:1b,Tags:["custom","apparition","justsummoned"],ArmorItems:[{},{},{},{id:"minecraft:diamond",Count:1b,tag:{CustomModelData:1}}]}
execute as @e[tag=apparition,tag=justsummoned] run function nyctocosm:mobs/nightmare_apparition/__generated__/block/15