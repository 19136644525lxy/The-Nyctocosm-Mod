#built using mc-build (https://github.com/mc-build/mc-build)

function nyctocosm:portal/scan/bottom
execute as @s[tag=success] at @s run function nyctocosm:portal/__generated__/block/109
execute as @s[tag=successX] at @s run function nyctocosm:portal/__generated__/block/110
execute as @s[tag=successZ] at @s run function nyctocosm:portal/__generated__/block/111
execute as @s[tag=successX] at @s run function nyctocosm:portal/activate
execute as @s[tag=successZ] at @s run function nyctocosm:portal/activatez
tp @s ~ -100 ~
kill @s