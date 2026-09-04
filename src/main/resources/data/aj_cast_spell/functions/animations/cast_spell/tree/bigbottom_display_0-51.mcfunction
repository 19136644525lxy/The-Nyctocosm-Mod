#built using mc-build (https://github.com/mc-build/mc-build)

execute if score .this aj.frame matches 0..7 run function aj_cast_spell:animations/cast_spell/tree/bigbottom_display_0-7
execute if score .this aj.frame matches 8..15 run function aj_cast_spell:animations/cast_spell/tree/bigbottom_display_8-15
execute if score .this aj.frame matches 16..23 run function aj_cast_spell:animations/cast_spell/tree/bigbottom_display_16-23
execute if score .this aj.frame matches 24..31 run function aj_cast_spell:animations/cast_spell/tree/bigbottom_display_24-31
execute if score .this aj.frame matches 32..39 run function aj_cast_spell:animations/cast_spell/tree/bigbottom_display_32-39
execute if score .this aj.frame matches 40 run data modify entity @s Pose.Head set value [0f,30f,0f]
execute if score .this aj.frame matches 51 run data modify entity @s Pose.Head set value [0f,30f,0f]