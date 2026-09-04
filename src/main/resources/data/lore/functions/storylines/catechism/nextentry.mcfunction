#built using mc-build (https://github.com/mc-build/mc-build)

execute unless score @s l.storyline1 matches 1.. run function lore:storylines/catechism/__generated__/block/5
execute if score @s l.storyline1 matches 1 run function lore:storylines/catechism/__generated__/block/6
execute unless score @s l.storyline1 matches 2.. run scoreboard players add @s l.storyline1 1