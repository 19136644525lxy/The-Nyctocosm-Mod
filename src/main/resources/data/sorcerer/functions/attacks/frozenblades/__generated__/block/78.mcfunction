#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s ns.frozenbladestimer 1
tag @s[scores={ns.frozenbladestimer=100..}] add kill
function sorcerer:attacks/frozenblades/procast
tag @s remove endcast
execute as @s[tag=kill] at @s run function sorcerer:attacks/frozenblades/__generated__/block/79