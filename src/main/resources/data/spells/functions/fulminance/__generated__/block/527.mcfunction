#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score @s PUUID0 run data get entity @s data.UUID[0]
execute store result score @s PUUID1 run data get entity @s data.UUID[1]
execute store result score @s PUUID2 run data get entity @s data.UUID[2]
execute store result score @s PUUID3 run data get entity @s data.UUID[3]
scoreboard players operation @s m.spell.cast_time = SpellTempStat m.spell.cast_time
scoreboard players operation @s m.spell.power = SpellTempStat m.spell.power
tag @s add s.casting
tag @s add Fulminance
scoreboard players operation TEMP s.circle_timer = @s m.spell.cast_time
summon armor_stand ~ ~ ~ {HasVisualFire:1b,Marker:1b,Invisible:1b,Tags:["spellcircle","Fulminance","justsummoned"],ArmorItems:[{},{},{},{id:"minecraft:glowstone_dust",Count:1b,tag:{CustomModelData:18}}]}
execute as @e[tag=spellcircle,tag=justsummoned] at @s run function spells:fulminance/__generated__/block/528
scoreboard players reset TEMP s.circle_timer
execute if score Fulminance s.type1 matches 1 positioned ~ ~1.5 ~ run function effects:elements/dream
execute if score Fulminance s.type2 matches 1 positioned ~ ~1.5 ~ run function effects:elements/radiance
execute if score Fulminance s.type3 matches 1 positioned ~ ~1.5 ~ run function effects:elements/resonance
execute if score Fulminance s.type4 matches 1 positioned ~ ~1.5 ~ run function effects:elements/nightmare
execute if score Fulminance s.type5 matches 1 positioned ~ ~1.5 ~ run function effects:elements/void
execute if score Fulminance s.type6 matches 1 positioned ~ ~1.5 ~ run function effects:elements/dissonance