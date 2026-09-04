#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s e.timer 1
execute if score @s e.timer matches 4.. run kill @s
particle flash ~ ~ ~ 0 0 0 0 1
execute if score @s e.timer matches 2 run function effects:elements/resonance/transmutate/__generated__/block/595
execute if score @s e.timer matches 3 run function effects:elements/resonance/transmutate/__generated__/block/596
execute if score @s e.timer matches 1 run function effects:elements/resonance/transmutate/__generated__/block/597
tp @s ^ ^ ^1