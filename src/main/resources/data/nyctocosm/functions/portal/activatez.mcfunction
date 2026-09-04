#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["portal","origin","activating","justsummoned","z"]}
execute as @e[tag=origin,tag=activating] at @s run function nyctocosm:portal/__generated__/block/45
function nyctocosm:portal/activation/z/sequence