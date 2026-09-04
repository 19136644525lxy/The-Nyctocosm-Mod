tag @s remove forwardfly
tag @s add forwardslide
function emissary:ai/phase/2/slide
execute at @s rotated ~ 0 run tp @s ^ ^ ^2
execute at @s run playsound nyctocosm:emissary.phase2.changeland master @a[distance=..300] ~ ~ ~ 50
