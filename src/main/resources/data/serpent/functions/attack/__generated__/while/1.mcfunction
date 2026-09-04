#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add SerpentTwirl timer 1
execute at @e[tag=serpent,tag=head] run function effects:nightmare_vein/single_random
execute as @e[tag=target,tag=serpent] at @s run function serpent:attack/__generated__/block/202
scoreboard players set #WHILE_3 LANG_MC_INTERNAL 1
schedule function serpent:attack/__generated__/while/0 1t