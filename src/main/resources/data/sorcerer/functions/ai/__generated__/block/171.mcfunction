#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set #execute LANG_MC_INTERNAL 0
execute if entity @a[distance=..7,sort=nearest,limit=1] run function sorcerer:ai/__generated__/conditional/6
execute if score #execute LANG_MC_INTERNAL matches 0 if entity @a[distance=8..20,sort=nearest,limit=1] run function sorcerer:ai/__generated__/conditional/7