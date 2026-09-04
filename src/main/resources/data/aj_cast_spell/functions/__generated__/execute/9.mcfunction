#built using mc-build (https://github.com/mc-build/mc-build)

tp @s[tag=aj.aj_cast_spell.bone.big] ^0 ^-1.813 ^0
tp @s[tag=aj.aj_cast_spell.bone.smalltop] ^0 ^-1.094 ^0
tp @s[tag=aj.aj_cast_spell.bone.bigtop] ^0 ^-0.469 ^0
tp @s[tag=aj.aj_cast_spell.bone.bigbottom] ^0 ^-2.969 ^0
tp @s[tag=aj.aj_cast_spell.bone.smallbottom] ^0 ^-2.532 ^0
execute store result score .calc aj.i run data get entity @s Air
execute store result entity @s Air short 1 run scoreboard players add .calc aj.i 2