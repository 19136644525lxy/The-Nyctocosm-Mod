#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set #WHILE_5 LANG_MC_INTERNAL 0
execute as @s[tag=!endraycast] positioned ^ ^ ^1 run function serpent:attack/beam/__generated__/while/7
execute if score #WHILE_5 LANG_MC_INTERNAL matches 0 run function serpent:attack/beam/__generated__/while/8