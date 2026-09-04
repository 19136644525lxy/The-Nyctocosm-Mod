#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set #execute LANG_MC_INTERNAL 0
execute if entity @s[tag=aj.aj_cast_spell.root] at @s run function aj_cast_spell:animations/cast_spell/__generated__/conditional/10
execute if score #execute LANG_MC_INTERNAL matches 0 run function aj_cast_spell:animations/cast_spell/__generated__/conditional/11