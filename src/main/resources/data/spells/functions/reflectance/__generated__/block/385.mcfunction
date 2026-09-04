#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation TEMP s.circle_timer = @s spelltimer
summon armor_stand ~ ~-100 ~ {Pose:{Head:[90f,0f,0f]},HasVisualFire:1b,Marker:1b,Invisible:1b,Tags:["spellcircle","Reflector","justsummoned"],ArmorItems:[{},{},{},{id:"minecraft:glowstone_dust",Count:1b,tag:{CustomModelData:10}}]}
execute as @e[tag=spellcircle,tag=justsummoned] run function spells:reflectance/__generated__/block/386
scoreboard players reset TEMP s.circle_timer