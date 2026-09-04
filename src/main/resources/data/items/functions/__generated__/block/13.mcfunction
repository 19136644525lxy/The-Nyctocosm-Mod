#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players reset @s click_bottle
function spells:bottle/subtract
function spells:cast_plane
function crafting:dais/cast
execute at @s positioned ~ ~1 ~ run function effects:soul_swirl/explode
execute at @s run playsound nyctocosm:spells.soul_release master @a[distance=..10]