#built using mc-build (https://github.com/mc-build/mc-build)

execute unless score @s l.storyline2 matches 1.. run function lore:storylines/dreams/__generated__/block/7
execute if score @s l.storyline2 matches 1 run function lore:storylines/dreams/__generated__/block/8
execute unless score @s l.storyline2 matches 2.. run scoreboard players add @s l.storyline2 1