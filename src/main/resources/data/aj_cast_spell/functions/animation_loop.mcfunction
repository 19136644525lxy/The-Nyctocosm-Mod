#built using mc-build (https://github.com/mc-build/mc-build)

schedule function aj_cast_spell:animation_loop 1t
scoreboard players set .aj.anim_loop aj.aj_cast_spell.animating 1
scoreboard players set .aj.animation aj.aj_cast_spell.animating 0
execute as @e[type=minecraft:marker,tag=aj.aj_cast_spell.root] run function aj_cast_spell:__generated__/execute/21
execute if score .aj.animation aj.aj_cast_spell.animating matches 0 run function aj_cast_spell:__generated__/execute/23