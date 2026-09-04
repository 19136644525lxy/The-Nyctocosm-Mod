#built using mc-build (https://github.com/mc-build/mc-build)

execute if entity @s[tag=aj.aj_cast_spell.bone.big] run execute if score .this aj.frame matches 0..51 run function aj_cast_spell:animations/cast_spell/tree/big_root_0-51
execute if entity @s[tag=aj.aj_cast_spell.bone.smalltop] run execute if score .this aj.frame matches 0..51 run function aj_cast_spell:animations/cast_spell/tree/smalltop_root_0-51
execute if entity @s[tag=aj.aj_cast_spell.bone.smallbottom] run execute if score .this aj.frame matches 0..51 run function aj_cast_spell:animations/cast_spell/tree/smallbottom_root_0-51
execute if entity @s[tag=aj.aj_cast_spell.bone.bigtop] run execute if score .this aj.frame matches 0..51 run function aj_cast_spell:animations/cast_spell/tree/bigtop_root_0-51
execute if entity @s[tag=aj.aj_cast_spell.bone.bigbottom] run execute if score .this aj.frame matches 0..51 run function aj_cast_spell:animations/cast_spell/tree/bigbottom_root_0-51
execute store result entity @s Air short 1 run scoreboard players get .this aj.frame