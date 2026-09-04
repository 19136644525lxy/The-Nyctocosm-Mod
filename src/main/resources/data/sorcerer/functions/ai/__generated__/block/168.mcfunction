#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set #execute LANG_MC_INTERNAL 0
execute if entity @a[distance=..15,sort=nearest,limit=1] run function sorcerer:ai/__generated__/conditional/0
execute if score #execute LANG_MC_INTERNAL matches 0 run function sorcerer:ai/__generated__/conditional/1