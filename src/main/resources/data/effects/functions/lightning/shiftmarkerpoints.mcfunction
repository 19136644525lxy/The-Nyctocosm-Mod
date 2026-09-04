#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add Lightning2 e.zigzagpoint 2
execute as @e[tag=lightningsegment,tag=!finalsegmentpoint] if score @s e.zigzagpoint = Lightning2 e.zigzagpoint at @s run function effects:lightning/__generated__/block/653
execute if score Lightning2 e.zigzagpoint < Lightning e.zigzagpoint run function effects:lightning/shiftmarkerpoints
scoreboard players reset Lightning2 e.zigzagpoint
scoreboard players reset Lightning e.zigzagpoint