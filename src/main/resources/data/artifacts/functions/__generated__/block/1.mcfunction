#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s a.hypertrace.timer 1
execute if score @s a.hypertrace.timer matches 200.. run function artifacts:__generated__/block/2
tag @s remove a.hypertrace.deplete