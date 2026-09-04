#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s m.spell.cast_time 1
execute if score @s m.spell.cast_time matches ..0 run function spells:casting/__generated__/block/200