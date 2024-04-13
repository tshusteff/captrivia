// Shuffle an array in place.  See https://stackoverflow.com/a/60662877.
// function shuffle<T>(arr: T[]): T[] {
const shuffle = <T,>(arr: T[]) => {
  let j, x, index;
  for (index = arr.length - 1; index > 0; index--) {
    j = Math.floor(Math.random() * (index + 1));
    x = arr[index];
    arr[index] = arr[j];
    arr[j] = x;
  }
}

export default {
  shuffle,
};
