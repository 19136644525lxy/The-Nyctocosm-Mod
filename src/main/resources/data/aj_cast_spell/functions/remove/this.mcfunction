#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set #execute LANG_MC_INTERNAL 0
execute if entity @s[tag=aj.aj_cast_spell.root] at @s run function aj_cast_spell:remove/__generated__/conditional/0
execute if score #execute LANG_MC_INTERNAL matches 0 run function aj_cast_spell:remove/__generated__/conditional/1