#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ~ ~ ~ ~ ~
scoreboard players operation .this aj.id = @s aj.id
scoreboard players operation .this aj.frame = @s aj.frame
scoreboard players set # aj.i 0
execute if entity @s[tag=aj.aj_cast_spell.anim.cast_spell] run function aj_cast_spell:__generated__/execute/13
execute if score # aj.i matches 0 run function aj_cast_spell:__generated__/execute/19
scoreboard players set #execute LANG_MC_INTERNAL 1