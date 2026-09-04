#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set SerpentSpeed value 3
scoreboard players set SerpentInterp value 3
tag @s add twirling
execute at @s positioned ~ ~10 ~ run tp @e[tag=serpent,tag=target] ^ ^ ^10 ~90 ~
function serpent:attack/__generated__/while/0