#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set #WHILE_3 LANG_MC_INTERNAL 0
execute unless score SerpentTwirl timer matches 61.. run function serpent:attack/__generated__/while/1
execute if score #WHILE_3 LANG_MC_INTERNAL matches 0 run function serpent:attack/__generated__/while/2