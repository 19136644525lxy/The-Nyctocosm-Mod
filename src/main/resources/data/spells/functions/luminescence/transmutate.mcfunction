#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set 20 value 20
scoreboard players operation TEMP s.circle_timer = 20 value
summon armor_stand ~ ~ ~ {HasVisualFire:1b,Marker:1b,Invisible:1b,Tags:["spellcircle","Luminescence","justsummoned"],ArmorItems:[{},{},{},{id:"minecraft:glowstone_dust",Count:1b,tag:{CustomModelData:7}}]}
execute as @e[tag=spellcircle,tag=justsummoned] at @s run function spells:luminescence/__generated__/block/233
scoreboard players reset TEMP s.circle_timer
playsound nyctocosm:spells.cast.luminescence master @a[distance=..10]
scoreboard players operation @a[distance=..1,tag=in_plane] m.spell.power = @s m.spell.power
execute as @a[distance=..1,tag=in_plane] at @s run function spells:luminescence/__generated__/block/234