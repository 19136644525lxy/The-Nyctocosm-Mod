#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set # aj.i 1
execute at @s as @e[type=#aj_cast_spell:bone_entities,tag=aj.aj_cast_spell.bone] if score @s aj.id = .this aj.id run function aj_cast_spell:__generated__/execute/14