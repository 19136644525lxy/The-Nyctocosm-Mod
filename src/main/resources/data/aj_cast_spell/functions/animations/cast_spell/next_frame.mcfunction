#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation .this aj.id = @s aj.id
scoreboard players operation .this aj.frame = @s aj.frame
execute rotated ~ 0 as @e[type=#aj_cast_spell:bone_entities,tag=aj.aj_cast_spell.bone,distance=..3.6] if score @s aj.id = .this aj.id run function aj_cast_spell:animations/cast_spell/tree/trunk
scoreboard players operation @s aj.frame += .aj.aj_cast_spell.framerate aj.i
scoreboard players set .aj.animation aj.aj_cast_spell.animating 1
execute unless score @s aj.frame matches 0..52 run function aj_cast_spell:animations/cast_spell/edge