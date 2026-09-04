#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s as @e[type=minecraft:area_effect_cloud,tag=aj.aj_cast_spell.bone] if score @s aj.id = .this aj.id run tp @s ~ ~ ~
function aj_cast_spell:reset