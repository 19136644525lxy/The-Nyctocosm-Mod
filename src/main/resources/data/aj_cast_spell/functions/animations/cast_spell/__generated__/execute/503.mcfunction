#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set #execute LANG_MC_INTERNAL 0
execute if score @s aj.frame matches ..1 run function aj_cast_spell:animations/cast_spell/__generated__/conditional/14
execute if score #execute LANG_MC_INTERNAL matches 0 run function aj_cast_spell:animations/cast_spell/__generated__/conditional/15